package ua.nure.kn156.legenka.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn156.legenka.User;

class HsqldbUserDAO implements UserDAO {
	private static final String UPDATE_QUERY = "UPDATE user SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
	private static final String FIND_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM user WHERE id= ? ";
	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM user";
	private static final String INSERT_QUERY = "INSERT INTO user (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String DELETE_QUERY = "DELETE FROM user WHERE id = ? ";
	private static final String SELECT_BY_NAMES = "SELECT id, firstname, lastname, dateofbirth FROM user WHERE firstname = ? AND lastname = ? ";
	private ConnectionFactory connectionFactory;
	
	public HsqldbUserDAO(){
	}
	
	public HsqldbUserDAO(ConnectionFactory connectionFactory){
		this.connectionFactory=connectionFactory;
	}

	@Override
	public User create(User user) throws DatabaseException {
		try{
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			int n = statement.executeUpdate();
			if(n!=1){
				throw new DatabaseException("Number of inserted rows:"+n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			User insertedUser = new User(user);
			if(keys.next()){
				insertedUser.setId(keys.getLong(1));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return insertedUser;
		}
		catch(DatabaseException e){
			throw e;
		}
		catch (SQLException e){
			throw new DatabaseException(e);
		}
	}

	@Override
	public User find(Long id) throws DatabaseException {
		try{
		User user = new User();
		Connection connection = connectionFactory.createConnection();
		PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
		statement.setLong(1,id);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
		user.setId(new Long(resultSet.getLong(1)));
		user.setFirstName(resultSet.getString(2));
		user.setLastName(resultSet.getString(3));
		user.setDateOfBirth(resultSet.getDate(4));
		resultSet.close();
		statement.close();
		connection.close();
		return user;
		}
		resultSet.close();
		statement.close();
		connection.close();
		 }
		catch(DatabaseException e){
			throw e;
		}
		catch (SQLException e){
			throw new DatabaseException(e);
		}
		return null;
	}

	@Override
	public void update(User user) throws DatabaseException {
		try{
		Connection connection = connectionFactory.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
		 statement.setString(1, user.getFirstName());
         statement.setString(2, user.getLastName());
         statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
         statement.setLong(4,user.getId());
         int n = statement.executeUpdate();
			if(n!=1){
				throw new DatabaseException("Update not successful");
			}
			statement.close();
			connection.close();
		 }
		catch(DatabaseException e){
			throw e;
		}
		catch (SQLException e){
			throw new DatabaseException(e);
		}
	}


	@Override
	public void delete(User user) throws DatabaseException {
		try{
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, user.getId());
	         int n = statement.executeUpdate();
				if(n!=1){
					throw new DatabaseException("Delete not successful");
				}
				statement.close();
				connection.close();
			 }
			catch(DatabaseException e){
				throw e;
			}
			catch (SQLException e){
				throw new DatabaseException(e);
			}

	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		Collection result= new LinkedList();
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while(resultSet.next()){
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);	
			}
			resultSet.close();
			statement.close();
			connection.close();
		}
		catch (DatabaseException e) {
			throw e;
			}
		catch (SQLException e) {
			throw new DatabaseException (e);
		}
		return result;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public Collection  find(String firstName, String lastName) throws DatabaseException{
			Collection result= new LinkedList();
			try {
				Connection connection = connectionFactory.createConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAMES);
				statement.setString(1,firstName);
				statement.setString(2,lastName);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()){
					User user = new User();
					user.setId(new Long(resultSet.getLong(1)));
					user.setFirstName(resultSet.getString(2));
					user.setLastName(resultSet.getString(3));
					user.setDateOfBirth(resultSet.getDate(4));
					result.add(user);	
				}
				resultSet.close();
				statement.close();
				connection.close();
			}
			catch (DatabaseException e) {
				throw e;
				}
			catch (SQLException e) {
				throw new DatabaseException (e);
			}
			return result;
		}

	}
