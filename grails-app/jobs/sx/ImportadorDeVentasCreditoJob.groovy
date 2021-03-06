package sx

class ImportadorDeVentasCreditoJob {

  def importadorDeVentasCredito

    static triggers = {
      cron name:   'impVtasCre',   startDelay: 20000, cronExpression: '0 0/15 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*            Importando Ventas Credito     ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

      try{
        importadorDeVentasCredito.importar()
      }catch(Exception e){
        e.printStackTrace()
      }

    }
}
