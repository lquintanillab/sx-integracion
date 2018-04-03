package sx

class ExportadorExistenciasJob {

  def exportadorDeExistencia

    static triggers = {
      cron name:   'expExistencia',   startDelay: 20000, cronExpression: '0 0/15 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Existencia                 *"
      println "*                                                          *"
      println "************************************************************"

        try{
          exportadorDeExistencia.exportar()
        }catch(Exception e){
            e.printStackTrace()
        }

    }
}