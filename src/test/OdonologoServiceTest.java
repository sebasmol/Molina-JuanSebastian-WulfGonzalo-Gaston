package test;

import com.backend.integrador.dao.impl.OdontologoDaoH2;
import com.backend.integrador.service.OdontologoService;

public class OdontologoServiceTest {
    OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());




}
