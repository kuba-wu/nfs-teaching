insert into SERIALIZED_SYSTEM values ('1', null, '{"receptors":[],"components":[{"id":"first","receptor":{"delay":5,"thresholds":[{"product": "first-product", "value": 6}]},"effector":{"product":"first-product","production":2,"outflow":0.1}}],"init":{}}');
insert into SERIALIZED_SYSTEM values ('2', null, '{"receptors":[],"components":[{"id":"first","receptor":{"delay":5,"thresholds":[{"product": "first-product", "value": 6}]},"effector":{"product":"first-product","production":3,"outflow":0.1}},{"id":"second","receptor":{"delay":1,"thresholds":[{"product": "second-product", "value": 12}]},"effector":{"product":"second-product","substrate":"first-product","production":2,"outflow":0.1}}],"init":{}}');
insert into SERIALIZED_SYSTEM values ('3', null, '{"receptors":[{"id":"external","delay":2,"thresholds":[{"product": "first-product", "value": 7}]}],"components":[{"id":"first","receptor":{"delay":3,"thresholds":[{"product": "first-product", "value": 10}]},"effector":{"substrate":"second-product","product":"first-product","production":2,"outflow":0.1}},{"id":"second","receptor":{"delay":1,"thresholds":[{"product": "second-product", "value": 7},{"product": "second-product", "signal": "external", "value": 3}]},"effector":{"product":"second-product","production":4,"outflow":0.1}}],"init":{"second-product":5}}');
insert into SERIALIZED_SYSTEM values ('4', null, '{"receptors":[],"components":[{"id":"first","receptor":{"delay":2,"thresholds":[{"product": "coordinating", "value": 8}]},"effector":{"product":"coordinating","production":3,"outflow":0.1}},{"id":"second","receptor":{"delay":1,"thresholds":[{"product": "coordinated", "value": 14}, {"product": "coordinating", "value": 10}]},"effector":{"product":"coordinated", "production":3,"outflow":0.1}}],"init":{}}');
insert into SERIALIZED_SYSTEM values ('5', null, '{"components": [ { "id": "hepatocite-glycogen-producer", "receptor": { "delay": 3, "thresholds": [ { "product": "glycogen", "value": 20 }, { "signal": "insulin", "product": "glycogen", "value": "40" } ] }, "effector": { "product": "glycogen", "substrate": "glucose", "production": "1", "outflow": "0.10" } }, { "id": "hepatocite-glucose-producer", "receptor": { "delay": 3, "thresholds": [ { "product": "glucose", "value": "20" }, { "signal": "insulin", "product": "glucose", "value": "5" } ] }, "effector": { "product": "glucose", "substrate": "glycogen", "production": "4", "outflow": "0.05" } }, { "id": "muscle", "receptor": { "delay": "2", "thresholds": [ { "product": "ATP", "value": "3" }, { "signal": "insulin", "product": "ATP", "value": "10" } ] }, "effector": { "product": "ATP", "substrate": "glucose", "production": 1, "outflow": "0.10" } }, { "id": "digestive-system", "receptor": { "delay": "2", "thresholds": [ { "product": "glycogen", "value": "30" } ] }, "effector": { "product": "glycogen", "production": "3", "outflow": "0.10" } } ], "receptors": [ { "delay": 0, "thresholds": [ { "product": "glucose", "value": 15 } ], "id": "insulin" } ], "init": {} }');
insert into SERIALIZED_SYSTEM_POSITION values ('5', '{"positions": [{"nodeId" : "digestive-system-effector", "x" : 30, "y" : 170},{"nodeId" : "digestive-system-receptor", "x" : 205, "y" : 170},{"nodeId" : "hepatocite-glucose-producer-effector", "x" : 248, "y" : 355},{"nodeId" : "hepatocite-glucose-producer-receptor", "x" : 480, "y" : 355},{"nodeId" : "hepatocite-glycogen-producer-effector", "x" : 462, "y" : 179},{"nodeId" : "hepatocite-glycogen-producer-receptor", "x" : 335, "y" : 33},{"nodeId" : "muscle-effector", "x" : 812, "y" : 370},{"nodeId" : "muscle-receptor", "x" : 931, "y" : 231	},{"nodeId" : "insulin", "x" : 714, "y" : 30}]}');
insert into SERIALIZED_SYSTEM values ('6', null, '{"components": [ { "id": "hepatocite-glycogen-producer", "receptor": { "delay": 3, "thresholds": [ { "product": "glycogen", "value": 20 }, { "signal": "insulin", "product": "glycogen", "value": "21" } ] }, "effector": { "product": "glycogen", "substrate": "glucose", "production": "1", "outflow": "0.10" } }, { "id": "hepatocite-glucose-producer", "receptor": { "delay": 3, "thresholds": [ { "product": "glucose", "value": "20" }, { "signal": "insulin", "product": "glucose", "value": "19" } ] }, "effector": { "product": "glucose", "substrate": "glycogen", "production": "4", "outflow": "0.05" } }, { "id": "muscle", "receptor": { "delay": "2", "thresholds": [ { "product": "ATP", "value": "3" }, { "signal": "insulin", "product": "ATP", "value": "4" } ] }, "effector": { "product": "ATP", "substrate": "glucose", "production": 1, "outflow": "0.10" } }, { "id": "digestive-system", "receptor": { "delay": "2", "thresholds": [ { "product": "glycogen", "value": "30" } ] }, "effector": { "product": "glycogen", "production": "3", "outflow": "0.10" } } ], "receptors": [ { "delay": "0", "thresholds": [ { "product": "glucose", "value": "15" } ], "id": "insulin" } ], "init": {} }');
insert into SERIALIZED_SYSTEM_POSITION values ('6', '{"positions": [{"nodeId" : "digestive-system-effector", "x" : 30, "y" : 170},{"nodeId" : "digestive-system-receptor", "x" : 205, "y" : 170},{"nodeId" : "hepatocite-glucose-producer-effector", "x" : 248, "y" : 355},{"nodeId" : "hepatocite-glucose-producer-receptor", "x" : 480, "y" : 355},{"nodeId" : "hepatocite-glycogen-producer-effector", "x" : 462, "y" : 179},{"nodeId" : "hepatocite-glycogen-producer-receptor", "x" : 335, "y" : 33},{"nodeId" : "muscle-effector", "x" : 812, "y" : 370},{"nodeId" : "muscle-receptor", "x" : 931, "y" : 231	},{"nodeId" : "insulin", "x" : 714, "y" : 30}]}');
insert into SERIALIZED_SYSTEM values ('7', null, '{"components": [ { "id": "first", "receptor": { "delay": "1", "thresholds": [ { "signal": null, "product": "first-product", "value": "8" }, { "signal": null, "product": "third-product", "value": "5" } ] }, "effector": { "product": "first-product", "substrate": null, "production": "2", "outflow": "0.10" } }, { "id": "second", "receptor": { "delay": "3", "thresholds": [ { "signal": null, "product": "second-product", "value": "12" }, { "signal": null, "product": "first-product", "value": "6" } ] }, "effector": { "product": "second-product", "substrate": null, "production": "1", "outflow": "0.10" } }, { "id": "third", "receptor": { "delay": "3", "thresholds": [ { "signal": null, "product": "third-product", "value": "11" }, { "signal": null, "product": "second-product", "value": "4" } ] }, "effector": { "product": "third-product", "substrate": null, "production": "2", "outflow": "0.10" } } ], "receptors": [], "init": {} }');
insert into SERIALIZED_SYSTEM values ('8', null, '{"components": [ { "id": "first", "receptor": { "delay": 1, "thresholds": [ { "signal": null, "product": "first-product", "value": "5" }, { "signal": null, "product": "fifth-product", "value": "8" } ] }, "effector": { "product": "first-product", "substrate": null, "production": "1", "outflow": "0.10" } }, { "id": "second", "receptor": { "delay": "2", "thresholds": [ { "signal": null, "product": "second-product", "value": "7" }, { "signal": null, "product": "first-product", "value": "3" } ] }, "effector": { "product": "second-product", "substrate": null, "production": 1, "outflow": "0.10" } }, { "id": "third", "receptor": { "delay": "3", "thresholds": [ { "signal": null, "product": "third-product", "value": "10" }, { "signal": null, "product": "second-product", "value": "6" } ] }, "effector": { "product": "third-product", "substrate": null, "production": "1", "outflow": "0.10" } }, { "id": "fourth", "receptor": { "delay": "3", "thresholds": [ { "signal": null, "product": "fourth-product", "value": "10" }, { "signal": null, "product": "third-product", "value": "6" } ] }, "effector": { "product": "fourth-product", "substrate": null, "production": "1", "outflow": "0.10" } }, { "id": "fifth", "receptor": { "delay": "3", "thresholds": [ { "signal": null, "product": "fifth-product", "value": "10" }, { "signal": null, "product": "fourth-product", "value": "6" } ] }, "effector": { "product": "fifth-product", "substrate": null, "production": "1", "outflow": "0.10" } } ], "receptors": [], "init": {} }');

insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T1', 'true', '1', 'Example task - single component', 'One component for testing purposes');
insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T2', 'true', '2', 'Example task - cooperation example', 'Two components for testing purposes');
insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T3', 'true', '3', 'Example task - cooperation with external receptor example', 'Two components with external receptor for testing purposes');
insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T4', 'true', '4', 'Example task - coordination example', 'Two components in coordination');
insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T5', 'true', '5', 'Example task - glucose level regulation example', 'Simulates regulation of glucose concentration in bloodstream. Consists of two hepatocites (synthesizing glycogen and delivering glucose), one muscle cell (utilizing glucose to produce ATP), one digestive system delivering glucagon and both alpha and beta cells of pancreas delivering insuline and glucagon.');
insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T6', 'true', '6', 'Student task 1 - glucose regulation', 'Simulates regulation of glucose concentration in bloodstream. Consists of two hepatocites (synthesizing glycogen and delivering glucose), one muscle cell (utilizing glucose to produce ATP), one digestive system delivering glucagon and beta cell of pancreas delivering insuline.');
insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T7', 'true', '7', 'Cycle 1 - three coordinated', 'Three components cooridnated in a cycle');
insert into TASK ("ID", "IS_PUBLIC", "SYSTEM_ID", "NAME", "DESCRIPTION") values ('T8', 'true', '8', 'Cycle 2 - five coordinated', 'Five components cooridnated in a cycle');

insert into USER ("LOGIN", "PASSWORD_HASH") values ('admin',   'edf11a0bc7fdd84db8f4c31a0e0c5e5651d013cc0d00069f63e2c475342f9c3737a62f1d68ae8e60');
insert into USER_ROLE ("USER_ID", "ROLES")  values ('admin', 'ROLE_ADMIN');
insert into USER_ROLE ("USER_ID", "ROLES")  values ('admin', 'ROLE_ASSISTANT');
insert into USER ("LOGIN", "PASSWORD_HASH") values ('asystent',   'edf11a0bc7fdd84db8f4c31a0e0c5e5651d013cc0d00069f63e2c475342f9c3737a62f1d68ae8e60');
insert into USER_ROLE ("USER_ID", "ROLES")  values ('asystent', 'ROLE_ASSISTANT');
insert into USER ("LOGIN", "PASSWORD_HASH") values ('student',   'edf11a0bc7fdd84db8f4c31a0e0c5e5651d013cc0d00069f63e2c475342f9c3737a62f1d68ae8e60');
insert into USER_ROLE ("USER_ID", "ROLES")  values ('student', 'ROLE_STUDENT');


insert into EXERCISE ("ID", "TASK_ID", "START", "END") values ('E1', 'T1', '2015-09-01 10:00:00', '2018-12-01 16:00:00');
insert into EXERCISE_ASSIGNEE ("USER_LOGIN", "EXERCISE_ID") values ('admin', 'E1');

insert into EXERCISE ("ID", "TASK_ID", "START", "END") values ('E2', 'T6', '2015-09-01 10:00:00', '2018-12-01 16:00:00');
insert into EXERCISE_ASSIGNEE ("USER_LOGIN", "EXERCISE_ID") values ('admin', 'E2');

insert into EXERCISE ("ID", "TASK_ID", "START", "END") values ('E3', 'T6', '2015-09-01 9:00:00', '2015-09-15 12:23:00');
insert into EXERCISE_ASSIGNEE ("USER_LOGIN", "EXERCISE_ID") values ('student', 'E3');
insert into EXERCISE ("ID", "TASK_ID", "START", "END") values ('E4', 'T6', '2016-09-16 9:00:00', '2018-09-15 12:23:00');
insert into EXERCISE_ASSIGNEE ("USER_LOGIN", "EXERCISE_ID") values ('student', 'E4');
