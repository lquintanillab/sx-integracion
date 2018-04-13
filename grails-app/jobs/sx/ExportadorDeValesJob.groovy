package sx

class ExportadorDeValesJob {

  def exportadorDeVales

    static triggers = {
       cron name:   'expVales',   startDelay: 10000, cronExpression: '0 0/3 * * * ?'
    }

    def execute() {
/*
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Vales                      *"
      println "*                                                          *"
      println "************************************************************"

      try {
          exportadorDeVales.exportar()
      }catch (Exception e){
          e.printStackTrace()
      }
    */  
    }
}
