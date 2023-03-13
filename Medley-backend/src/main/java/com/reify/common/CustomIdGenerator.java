package com.reify.common;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


public class CustomIdGenerator implements IdentifierGenerator, Configurable {

    private String prefix;
    private String seqName;

    private String seqLength;

    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {

        String nextValue = null;
        Connection connection = null;
        try{
            connection = session.connection();
            Statement statement = connection.createStatement();
            String query = "select LPAD(" + seqName + "," + seqLength + ",'0') as nextval from dual";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                nextValue = rs.getString(1);
            }

        } catch (Exception e) {
            throw new RuntimeException("Exception while creating custom Id ");
        }

        return prefix + nextValue;
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
        seqName = properties.getProperty("seqName")+".nextval";
        seqLength = properties.getProperty("seqLength");
    }
}
