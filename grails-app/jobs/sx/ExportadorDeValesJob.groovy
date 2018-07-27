package sx

class ExportadorDeValesJob {

  def exportadorDeVales

    static triggers = {
       cron name:   'expVales',   startDelay: 10000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Vales  ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

      try {
          exportadorDeVales.exportar()
      }catch (Exception e){
          e.printStackTrace()
      }

    }
}
