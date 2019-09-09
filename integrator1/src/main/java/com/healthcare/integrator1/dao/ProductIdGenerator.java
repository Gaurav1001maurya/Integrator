package com.healthcare.integrator1.dao;

import java.io.Serializable;
import java.sql.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ProductIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {

		String prefix = "PDT";
		Connection connection = session.connection();

		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("SELECT  productId FROM  Product ORDER BY productId DESC LIMIT 1 ");

			if (rs.next()) {
				String id = rs.getString(1);
				String ids = id.substring(3);
				int idss = Integer.parseInt(ids) + 1;
				String generatedId = prefix + new Integer(idss).toString();
				System.out.println("Generated Id: " + generatedId);
				return generatedId;
			} else {
				return "PDT101";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

}