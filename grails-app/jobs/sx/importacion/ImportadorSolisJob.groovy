package sx.importacion

class ImportadorSolisJob {

  def importadorDeExistencias
  def importadorDeClientes
  def importadorDeVales
  def importadorDeTraslados

    static triggers = {
    cron name:   'impSolis2',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {

     println "************************************************************"
     println "*                                                          *"
     println "*                    Importando Solis Dos ${new Date()}                 *"
     println "*                                                          *"
     println "************************************************************"

     def sucursal = 'SOLIS'

         try{
           //println "importando Existencias: "+sucursal
            importadorDeExistencias.importar(sucursal)
         }catch (Exception e){
                e.printStackTrace()
        }
        try{
           //println "importando Clientes: "+sucursal
           importadorDeClientes.importar(sucursal)
        }catch (Exception e){
               e.printStackTrace()
       }
       try{
        //  println "importando ComunicacionEmpresa: "+sucursal
          importadorDeClientes.importarComunicacionEmpresa(sucursal)
       }catch (Exception e){
              e.printStackTrace()
      }

  }
}
