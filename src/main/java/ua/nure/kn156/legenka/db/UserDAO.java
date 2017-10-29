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

User find(Long id) throws DatabaseException;

void update(User user) throws DatabaseException;

void delete(User user) throws DatabaseException;

Collection<User> findAll() throws DatabaseException;

void setConnectionFactory(ConnectionFactory connectionFactory);
}
