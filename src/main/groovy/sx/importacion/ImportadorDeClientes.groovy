package sx.importacion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sx.DataSourceReplica
import sx.EntityConfiguration
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.simple.SimpleJdbcInsert



@Component
class ImportadorDeClientes{


  @Autowired
   @Qualifier('dataSourceLocatorService')
  def dataSourceLocatorService
  @Autowired
  @Qualifier('dataSource')
  def dataSource
  @Autowired
  @Qualifier('replicaService')
  def replicaService


  def importar(){
    replicaService.importar('Cliente')
  }

  def importar(nombreSuc){
    replicaService.importar('Cliente',nombreSuc)
  }

  def importarComunicacionEmpresa(){
    replicaService.importar('ComunicacionEmpresa')
  }

  def importarComunicacionEmpresa(nombreSuc){
    replicaService.importar('ComunicacionEmpresa',nombreSuc)
  }

}
