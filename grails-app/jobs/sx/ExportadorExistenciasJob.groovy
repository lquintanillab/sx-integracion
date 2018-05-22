package sx

class ExportadorExistenciasJob {

  def exportadorDeExistencia

    static triggers = {
      cron name:   'expExistencia',   startDelay: 20000, cronExpression: '0 0/10 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Existencia    ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

        try{
          exportadorDeExistencia.exportar()
        }catch(Exception e){
            e.printStackTrace()
        }

    }
}
