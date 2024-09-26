select 
	u.username AS usuario,
	r.role_type AS rol,
	p.name AS permiso
from
	users u
inner join
		user_roles ur on
	u.id = ur.user_id
inner join
	roles r on
	ur.role_id = r.id
inner join role_permissions rp on
	r.id = rp.role_id
inner join permissions p on
	rp.permission_id = p.id