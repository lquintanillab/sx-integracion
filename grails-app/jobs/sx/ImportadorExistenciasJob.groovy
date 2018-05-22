package sx

class ImportadorExistenciasJob {

  def importadorDeExistencias

    static triggers = {
      cron name:   'impExistencia',   startDelay: 20000, cronExpression: '0 0/15 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Importando Existencia     ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

      try{
        importadorDeExistencias.importar()
      }catch(Exception e){
        e.printStackTrace()
      }

    }
}
