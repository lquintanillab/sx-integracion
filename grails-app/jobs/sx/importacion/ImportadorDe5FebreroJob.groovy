package sx.importacion

class ImportadorDe5FebreroJob {

  def importadorDeExistencias
  def importadorDeClientes

    static triggers = {
    cron name:   'imp5Febrero',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
     println "************************************************************"
     println "*                                                          *"
     println "*                    Importando 5 Febrero                  *"
     println "*                                                          *"
     println "************************************************************"

     def sucursal = 'CF5FEBRERO'

     try{
        importadorDeExistencias.importar(sucursal)
     }catch (Exception e){
            e.printStackTrace()
    }
    try{
       importadorDeClientes.importar(sucursal)
    }catch (Exception e){
           e.printStackTrace()
   }


    }
}
