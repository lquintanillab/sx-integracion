package sx.exportacion

class ExportadorDeAndradeJob {

  def exportadorDeClientes
  def exportadorDeClientesCredito
  def exportadorDeProductos
  def exportadorDeExistencia

    static triggers = {
      cron name:   'expAndrade',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Andrade                    *"
      println "*                                                          *"
      println "************************************************************"

      def sucursal = 'ANDRADE'

            try{
               exportadorDeClientesCredito.exportar(sucursal)
            }catch (Exception e){
                   e.printStackTrace()
           }
           try{
              exportadorDeClientes.exportar(sucursal)
           }catch (Exception e){
                  e.printStackTrace()
            }
            try{
               exportadorDeProductos.exportar(sucursal)
            }catch (Exception e){
                   e.printStackTrace()
           }
           try{
              exportadorDeExistencia.exportar(sucursal)
           }catch (Exception e){
                  e.printStackTrace()
          }
    }


}