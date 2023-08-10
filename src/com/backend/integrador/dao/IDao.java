package com.backend.integrador.dao;

import java.util.List;

public interface IDao<T>{
    //alta y listarlos
    T registrar(T t);
    List<T> listarTodos();
}
