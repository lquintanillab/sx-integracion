package sx

class ExportadorDepositosJob {

  def exportadorDeDepositos

    static triggers = {
       cron name:   'expDepositos',   startDelay: 10000, cronExpression: '0 0/5 * ? * MON-SAT'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Depositos                  *"
      println "*           ${new Date()}                   *"
      println "*                                                          *"
      println "************************************************************"

      try {
          exportadorDeDepositos.exportar()
      }catch (Exception e){
          e.printStackTrace()
      }
     
    }
}
