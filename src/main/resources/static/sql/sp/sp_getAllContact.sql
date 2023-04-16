DELIMITER //
drop procedure if exists sp_getAllContact;
CREATE PROCEDURE sp_getAllContact  (IN userId int)
BEGIN
select
 uc.user_contact_id,
 uc.CONTACT_PERSON_NAME,
 uc.CONTACT_PERSON_NICKNAME,
 uc.EMAIL,
 uc.PHONE_NUMBER,
 uc.WORK,
 uc.DESC,
 uc.USER_ID,
 uc.profilePath
 
 from my_user mu 
 inner join user_contact uc on uc.user_id=mu.user_id
 and uc.user_id=userId and uc.is_deleted is not true;

END;
  call sp_getAllContact(10);
