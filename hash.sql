CREATE TABLE `hashserver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `height` int(11) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `four` int(11) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `updatetime` bigint(50) DEFAULT NULL,
  `timeDiff` int(11) DEFAULT NULL COMMENT '时间差',
  `userId1` int(11) DEFAULT NULL,
  `power1` int(11) DEFAULT NULL,
  `userId2` int(11) DEFAULT NULL,
  `power2` int(11) DEFAULT NULL,
  `userId3` int(11) DEFAULT NULL,
  `power3` int(11) DEFAULT NULL,
  `hash1` varchar(225) DEFAULT NULL,
  `hash2` varchar(225) DEFAULT NULL,
  `hash3` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='2万节点  1个hash tocket*2'