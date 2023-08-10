package com.backend.integrador.dao.impl;

import com.backend.integrador.dao.H2Connection;
import com.backend.integrador.dao.IDao;
import com.backend.integrador.entity.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private Logger Logger;
    private final Logger LOGGER = Logger.getLogger(String.valueOf(OdontologoDaoH2.class));


    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologo1 = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement("INSERT INTO ODONTOLOGOS (NOMBRE, APELLIDO, MATRICULA,) VALUES (?, ?, ?,)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, odontologo.getNombre());
            ps.setString(2, odontologo.getApellido());
            ps.setInt(3, odontologo.getNumeroMatricula());
            ps.execute();

            odontologo1 = new Odontologo(odontologo.getNombre(), odontologo.getApellido(), odontologo.getNumeroMatricula());

            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()){
                odontologo.setId(rs.getInt(1));
            }
            connection.commit();
            if (odontologo1 == null) LOGGER.error("No fue posible registrar al odontologo");
            else LOGGER.info("Se ha registrado el odontologo: " + odontologo1);

        } catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if(connection != null){
                try {
                    connection.rollback();
                    System.out.println("Tuvimos un problema");
                    e.printStackTrace();
                } catch (SQLException exception){
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        }
        return odontologo1;
    }

    @Override
    public List<Odontologo> listarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ODONTOLGOS");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Odontologo odontologo = crearObjetoOdontologo(rs);
                odontologos.add(odontologo);
            }

            LOGGER.info("Listado de todos los odontologos: " + odontologos);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

    }
        return odontologos;
}
    private Odontologo crearObjetoOdontologo(ResultSet resultSet) throws SQLException {
        int idOdontologo = resultSet.getInt("id");
        String nombreOdontologo = resultSet.getString("nombre");
        String apellidoOdontologo = resultSet.getString("apellido");
        int matriculaOdontologo = resultSet.getInt("matricula");

        return new Odontologo(nombreOdontologo, apellidoOdontologo, matriculaOdontologo);
    }
}
