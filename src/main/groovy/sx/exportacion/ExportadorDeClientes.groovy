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
class ExportadorDeClientes{

  @Autowired
   @Qualifier('dataSourceLocatorService')
  def dataSourceLocatorService
  @Autowired
  @Qualifier('dataSource')
  def dataSource
  @Autowired
  @Qualifier('replicaService')
  def replicaService


  def exportar(){
    replicaService.exportar('Cliente')
  }

  def exportar(nombreSuc){
    replicaService.exportar('Cliente',nombreSuc)
  }


  def exportarComunicacionEmpresa(){
    replicaService.exportar('ComunicacionEmpresa')
  }

  def exportarComunicacionEmpresa(nombreSuc){
    replicaService.exportar('ComunicacionEmpresa',nombreSuc)
  }

}
