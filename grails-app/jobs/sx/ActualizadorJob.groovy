package sx

class ActualizadorJob {
    def  actualizacionCredito
    static triggers = {
      cron name:   'act',   startDelay: 10000, cronExpression: '0 0 22 * * ?'
    }

    def execute() {

        try{
            actualizacionCredito.actualizarSaldo()
        }catch(Exception e){
          e.printStackTrace()
        }
        try{
            actualizacionCredito.actualizarAtraso()
        }catch(Exception e){
          e.printStackTrace()
        }
        
    }
}
