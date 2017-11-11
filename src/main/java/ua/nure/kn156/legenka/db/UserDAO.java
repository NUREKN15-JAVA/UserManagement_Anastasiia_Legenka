package ua.nure.kn156.legenka.db;

import java.util.Collection;
// для каждого интерфейса написать спецификацию, возможно расширить интерфейс
import ua.nure.kn156.legenka.User;
/**
 * Interface for User class which implements DAO pattern  with all CRUD opps
 * @author Nastya
 *
 */

public interface UserDAO {
	/**
	 * Add user into DB users table and get new user`s id from db
	 * @param user all fields of user must be non-null except id field
	 * @return copy of user from db with id auto created
	 * @throws DatabaseException in case of any error with db
	 */
User create(User user) throws DatabaseException;
/**
 * Find user in DB users table by id
 * @param user id that must be found, must be not null
 * @return copy of user from db with specified id, if such id doesn't exist return null
 * @throws DatabaseException in case of any error with db
 */
User find(Long id) throws DatabaseException;
/**
 * Update user in DB users table
 * @param user that must be updated, all fields of user must be non-null
 * @throws DatabaseException in case of any error with db
 */
void update(User user) throws DatabaseException;
/**
 * Delete user from DB users table
 * @param user that must be deleted, all fields of user must be non-null
 * @throws DatabaseException in case of any error with db
 */
void delete(User user) throws DatabaseException;
/**
 * Find all users in DB users table
 * @return list of all users in db
 * @throws DatabaseException in case of any error with db
 */
Collection<User> findAll() throws DatabaseException;

void setConnectionFactory(ConnectionFactory connectionFactory);
}
