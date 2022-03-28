DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterInsert.UpdateTotalPurchasesPerSp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterInsert.UpdateTotalPurchasesPerSpAndVp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterInsert.UpdateTotalValuePerSp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterInsert.UpdateTotalValuePerSpWithOp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterInsert.UpdateAvgAmountOpPerSp`;

DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterUpdate.UpdateTotalPurchasesPerSp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterUpdate.UpdateTotalPurchasesPerSpAndVp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterUpdate.UpdateTotalValuePerSp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterUpdate.UpdateTotalValuePerSpWithOp`;
DROP TRIGGER IF EXISTS `db2_project`.`Order.AfterUpdate.UpdateAvgAmountOpPerSp`;

DROP TRIGGER IF EXISTS `db2_project`.`Order_OptionalProduct.AfterInsert.UpdateTotalPurchasesPerOp`;

DELIMITER $$
USE `db2_project` $$

-- 1.update total purchases per service package
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterInsert.UpdateTotalPurchasesPerSp` AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
    		IF EXISTS (SELECT * FROM `mv_total_purchases_per_sp` AS tp
			        WHERE new.servicePackageId = tp.servicePackageId) THEN
			UPDATE `mv_total_purchases_per_sp` AS tp
				SET tp.totalPurchases = (SELECT count(*) FROM `order` AS o
									WHERE o.servicePackageId = new.servicePackageId AND o.valid = 1
                                    GROUP BY o.servicePackageId)
			WHERE new.servicePackageId = tp.servicePackageId;
		ELSE 
			INSERT INTO `mv_total_purchases_per_sp` (servicePackageId, totalPurchases)
			VALUE (new.servicePackageId, (SELECT count(*) FROM `order` AS o
									WHERE o.servicePackageId = new.servicePackageId AND o.valid = 1
                                    GROUP BY o.servicePackageId));
		END IF;
	END IF;
END$$

-- 2.update total purchases per service package and validity period
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterInsert.UpdateTotalPurchasesPerSpAndVp` AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN        
		IF EXISTS (SELECT * FROM `mv_total_purchases_per_sp_and_vp` AS tpsv
					WHERE new.servicePackageId = tpsv.servicePackageId AND new.validityPeriodId = tpsv.validityPeriodId) THEN
			UPDATE `mv_total_purchases_per_sp_and_vp` AS tpsv
				SET tpsv.totalPurchases = (SELECT count(*) FROM `order` AS o
									WHERE o.servicePackageId = new.servicePackageId AND o.validityPeriodId = new.validityPeriodId AND o.valid = 1
                                    GROUP BY o.servicePackageId, o.validityPeriodId)
			WHERE new.servicePackageId = tpsv.servicePackageId AND new.validityPeriodId = tpsv.validityPeriodId;
		ELSE 
			INSERT INTO `mv_total_purchases_per_sp_and_vp` (servicePackageId, validityPeriodId, totalPurchases)
			VALUES (new.servicePackageId, new.validityPeriodId, (SELECT count(*) FROM `order` AS o
									WHERE o.servicePackageId = new.servicePackageId AND o.validityPeriodId = new.validityPeriodId AND o.valid = 1
                                    GROUP BY o.servicePackageId, o.validityPeriodId));
		END IF;
	END IF;
END$$

-- 3.update total value per service package
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterInsert.UpdateTotalValuePerSp` AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
        IF EXISTS (SELECT * FROM `mv_total_value_per_sp` AS tv
				WHERE new.servicePackageId = tv.servicePackageId) THEN
			UPDATE `mv_total_value_per_sp` AS tv
				SET tv.totalValue_euro = (SELECT COALESCE(sum(vp.monthlyFee_euro),0) FROM `validity_period` AS vp, `order` AS o
								WHERE o.servicePackageId = new.servicePackageId AND o.valid = 1 AND vp.validityPeriodId = o.validityPeriodId)
			WHERE new.servicePackageId = tv.servicePackageId;
		ELSE 
			INSERT INTO `mv_total_value_per_sp` (servicePackageId, totalValue_euro)
            VALUES (new.servicePackageId, (SELECT COALESCE(sum(vp.monthlyFee_euro),0) FROM `validity_period` AS vp, `order` AS o
								WHERE o.servicePackageId = new.servicePackageId AND
                                o.valid = 1 AND vp.validityPeriodId = o.validityPeriodId));
		END IF;
	END IF;
END$$

-- 4.update total value per service package with optional product
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterInsert.UpdateTotalValuePerSpWithOp` AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
        IF EXISTS (SELECT * FROM `mv_total_value_per_sp_with_op` AS tvso
				WHERE new.servicePackageId = tvso.servicePackageId) THEN
			UPDATE `mv_total_value_per_sp_with_op` AS tvso
				SET tvso.totalValue_euro = (SELECT COALESCE(sum(o.totalCost_euro),0) FROM `order` AS o
										WHERE o.servicePackageId = new.servicePackageId AND o.valid = 1)
			WHERE new.servicePackageId = tvso.servicePackageId;
		ELSE
			INSERT INTO `mv_total_value_per_sp_with_op` (servicePackageId, totalValue_euro)
            VALUES (new.servicePackageId, (SELECT COALESCE(sum(o.totalCost_euro),0) FROM `order` AS o
										WHERE o.servicePackageId = new.servicePackageId AND o.valid = 1));
		END IF;
	END IF;
END$$

-- 5.update average amount of optional products sold with each package
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterInsert.UpdateAvgAmountOpPerSp` AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
        IF EXISTS (SELECT * FROM `mv_avg_amount_op_per_sp` AS aaos
				WHERE new.servicePackageId = aaos.servicePackageId) THEN
			UPDATE `mv_avg_amount_op_per_sp` AS aaos
				SET aaos.avgAmountOptionalProducts = (SELECT COALESCE(avg(o.amountOptionalProducts),0) FROM `order` AS o
													WHERE new.servicePackageId = o.servicePackageId AND o.valid = 1
                                                    GROUP BY o.servicePackageId)
			WHERE new.servicePackageId = aaos.servicePackageId;
		ELSE 
			INSERT INTO `mv_avg_amount_op_per_sp` (servicePackageId, avgAmountOptionalProducts)
            VALUES (new.servicePackageId, (SELECT COALESCE(avg(o.amountOptionalProducts),0) FROM `order` AS o
													WHERE new.servicePackageId = o.servicePackageId AND o.valid = 1
                                                    GROUP BY o.servicePackageId));
		END IF;
	END IF;
END$$

-- 1.update total purchases per service package
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterUpdate.UpdateTotalPurchasesPerSp` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
		IF EXISTS (SELECT * FROM `mv_total_purchases_per_sp`
					WHERE new.servicePackageId = servicePackageId) THEN
			UPDATE `mv_total_purchases_per_sp` 
				SET totalPurchases = (SELECT count(*) FROM `order`
									WHERE servicePackageId = new.servicePackageId AND valid = 1
                                    GROUP BY servicePackageId)
			WHERE new.servicePackageId = servicePackageId;
		ELSE 
			INSERT INTO `mv_total_purchases_per_sp` (servicePackageId, totalPurchases)
			VALUE (new.servicePackageId, (SELECT count(*) FROM `order`
									WHERE servicePackageId = new.servicePackageId AND valid = 1
                                    GROUP BY servicePackageId));
		END IF;
	END IF;
END$$

-- 2.update total purchases per service package and validity period
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterUpdate.UpdateTotalPurchasesPerSpAndVp` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN  
		IF EXISTS (SELECT * FROM `mv_total_purchases_per_sp_and_vp`
					WHERE new.servicePackageId = servicePackageId AND new.validityPeriodId = validityPeriodId) THEN
			UPDATE `mv_total_purchases_per_sp_and_vp`
				SET totalPurchases = (SELECT count(*) FROM `order`
									WHERE servicePackageId = new.servicePackageId AND
                                    validityPeriodId = new.validityPeriodId AND valid = 1
                                    GROUP BY servicePackageId, validityPeriodId)
			WHERE new.servicePackageId = servicePackageId AND new.validityPeriodId = validityPeriodId;
		ELSE 
			INSERT INTO `mv_total_purchases_per_sp_and_vp` (servicePackageId, validityPeriodId, totalPurchases)
			VALUES (new.servicePackageId, new.validityPeriodId, (SELECT count(*) FROM `order`
									WHERE servicePackageId = new.servicePackageId AND
                                    validityPeriodId = new.validityPeriodId AND valid = 1
                                    GROUP BY servicePackageId, validityPeriodId));
		END IF;
	END IF;
END$$

-- 3.update total value per service package
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterUpdate.UpdateTotalValuePerSp` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
        IF EXISTS (SELECT * FROM `mv_total_value_per_sp` AS tv
				WHERE new.servicePackageId = tv.servicePackageId) THEN
			UPDATE `mv_total_value_per_sp` AS tv
				SET tv.totalValue_euro = (SELECT COALESCE(sum(vp.monthlyFee_euro),0) FROM `validity_period` AS vp, `order` AS o
								WHERE o.servicePackageId = new.servicePackageId AND
                                o.valid = 1 AND vp.validityPeriodId = o.validityPeriodId)
			WHERE new.servicePackageId = tv.servicePackageId;
		ELSE 
			INSERT INTO `mv_total_value_per_sp` (servicePackageId, totalValue_euro)
            VALUES (new.servicePackageId, (SELECT COALESCE(sum(vp.monthlyFee_euro),0) FROM `validity_period` AS vp, `order` AS o
								WHERE o.servicePackageId = new.servicePackageId AND
                                o.valid = 1 AND vp.validityPeriodId = o.validityPeriodId));
		END IF;
	END IF;
END$$

-- 4.update total value per service package with optional product
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterUpdate.UpdateTotalValuePerSpWithOp` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
        IF EXISTS (SELECT * FROM `mv_total_value_per_sp_with_op` AS tvso
				WHERE new.servicePackageId = tvso.servicePackageId) THEN
			UPDATE `mv_total_value_per_sp_with_op` AS tvso
				SET tvso.totalValue_euro = (SELECT COALESCE(sum(o.totalCost_euro),0) FROM `order` AS o
										WHERE o.servicePackageId = new.servicePackageId AND o.valid = 1)
			WHERE new.servicePackageId = tvso.servicePackageId;
		ELSE
			INSERT INTO `mv_total_value_per_sp_with_op` (servicePackageId, totalValue_euro)
            VALUES (new.servicePackageId, (SELECT COALESCE(sum(o.totalCost_euro),0) FROM `order` AS o
										WHERE o.servicePackageId = new.servicePackageId AND o.valid = 1));
		END IF;
	END IF;
END$$

-- 5.update average amount of optional products sold with each package
CREATE DEFINER=`root`@`localhost` TRIGGER `Order.AfterUpdate.UpdateAvgAmountOpPerSp` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF new.valid = 1 THEN
        IF EXISTS (SELECT * FROM `mv_avg_amount_op_per_sp` AS aaos
				WHERE new.servicePackageId = aaos.servicePackageId) THEN
			UPDATE `mv_avg_amount_op_per_sp` AS aaos
				SET aaos.avgAmountOptionalProducts = (SELECT COALESCE(avg(o.amountOptionalProducts),0) FROM `order` AS o
													WHERE new.servicePackageId = o.servicePackageId AND o.valid = 1
                                                    GROUP BY o.servicePackageId)
			WHERE new.servicePackageId = aaos.servicePackageId;
		ELSE 
			INSERT INTO `mv_avg_amount_op_per_sp` (servicePackageId, avgAmountOptionalProducts)
            VALUES (new.servicePackageId, (SELECT COALESCE(avg(o.amountOptionalProducts),0) FROM `order` AS o
													WHERE new.servicePackageId = o.servicePackageId AND o.valid = 1
                                                    GROUP BY o.servicePackageId));
		END IF;
	END IF;
END$$

-- update total purchases per optional product
CREATE DEFINER=`root`@`localhost` TRIGGER `Order_OptionalProduct.AfterInsert.UpdateTotalPurchasesPerOp` AFTER INSERT ON `order__optional_product` FOR EACH ROW BEGIN
    IF EXISTS (SELECT * FROM `order` AS o
			WHERE o.orderId = new.orderId AND o.valid = 1) THEN
		IF EXISTS (SELECT * FROM `mv_total_purchases_per_op` AS tpop
				WHERE tpop.optionalProductId = new.optionalProductId) THEN
			UPDATE `mv_total_purchases_per_op` AS tpop
				SET tpop.totalPurchases = (SELECT count(*) FROM `order__optional_product` AS oop, `order` AS o
										WHERE oop.optionalProductId = new.optionalProductId AND
                                        oop.orderId = o.orderId AND o.valid = 1
                                        GROUP BY oop.optionalProductId)
			WHERE tpop.optionalProductId = new.optionalProductId;
		ELSE 
			INSERT INTO `mv_total_purchases_per_op` (optionalProductId, totalPurchases)
            VALUES (new.optionalProductId, (SELECT count(*) FROM `order__optional_product` AS oop, `order` AS o
										WHERE oop.optionalProductId = new.optionalProductId AND
                                        oop.orderId = o.orderId AND o.valid = 1
                                        GROUP BY oop.optionalProductId));
        END IF;
	END IF;
END$$
