package sx

class ImportadorDeCxcJob {

  def importadorDeCxc

    static triggers = {
       cron name:   'impCxc',   startDelay: 10000, cronExpression: '0 0 20 * * ?'
    }

    def execute() {
/*
      println "************************************************************"
      println "*                                                          *"
      println "*              Importador De CuentaPorCobrar               *"
      println "*                                                          *"
      println "************************************************************"

      try{
        importadorDeCxc.importarOperacionesVenta()
      }catch(Exception e){
        e.printStackTrace()
      }

*/
    }
}
