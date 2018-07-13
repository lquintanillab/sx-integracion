package sx.exportacion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sx.DataSourceReplica
import sx.EntityConfiguration
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.simple.SimpleJdbcInsert



@Component
class ExportadorDeProveedores{

  @Autowired
  @Qualifier('replicaService')
  def replicaService


  def exportar(){
    replicaService.exportar('Proveedor')
  }

  def exportar(nombreSuc){
    replicaService.exportar('Proveedor',nombreSuc)
  }

  def exportarProductos(){
      replicaService.exportar('ProveedorProductos')
  }

}
