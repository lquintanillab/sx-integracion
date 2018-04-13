package sx

class ImportadorDeVentasCreditoJob {

  def importadorDeVentasCredito

    static triggers = {
      cron name:   'impVtasCre',   startDelay: 20000, cronExpression: '0 0/10 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*            Importando Ventas Credito                     *"
      println "*                                                          *"
      println "************************************************************"

      try{
        importadorDeVentasCredito.importar()
      }catch(Exception e){
        e.printStackTrace()
      }

    }
}
