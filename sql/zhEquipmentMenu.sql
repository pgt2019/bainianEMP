-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台 设备接口绑定', '3', '1', '/console/zhEquipment', 'C', '0', 'console:zhEquipment:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '振汇开放平台 设备接口绑定菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台 设备接口绑定查询', @parentId, '1',  '#',  'F', '0', 'console:zhEquipment:list',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台 设备接口绑定新增', @parentId, '2',  '#',  'F', '0', 'console:zhEquipment:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台 设备接口绑定修改', @parentId, '3',  '#',  'F', '0', 'console:zhEquipment:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台 设备接口绑定删除', @parentId, '4',  '#',  'F', '0', 'console:zhEquipment:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');
