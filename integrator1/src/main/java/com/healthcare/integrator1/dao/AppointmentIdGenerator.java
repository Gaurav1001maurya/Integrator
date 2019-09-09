package com.healthcare.integrator1.dao;

import java.io.Serializable;
import java.sql.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class AppointmentIdGenerator implements IdentifierGenerator{

    @Override
    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {

        String prefix = "APT";
        Connection connection = session.connection();

        try {
            Statement statement=connection.createStatement();

            ResultSet rs=statement.executeQuery("SELECT  appointmentId FROM  Appointment ORDER BY appointmentId DESC LIMIT 1 ");

            if(rs.next())
            {
            	String id = rs.getString(1);
            	String ids = id.substring(3);
            	int idss  = Integer.parseInt(ids)+1;
                String generatedId = prefix + new Integer(idss).toString();
                System.out.println("Generated Id: " + generatedId);
                return generatedId;
            }
            else {
            	return "APT101";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

}