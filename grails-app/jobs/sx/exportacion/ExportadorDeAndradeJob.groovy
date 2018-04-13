package sx.exportacion

class ExportadorDeAndradeJob {

  def exportadorDeClientes
  def exportadorDeClientesCredito
  def exportadorDeProductos
  def exportadorDeExistencia
  def exportadorDeVales
  def exportadorDeTraslados

    static triggers = {
      cron name:   'expAndrade',   startDelay: 10000, cronExpression: '0 0/7 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Andrade                    *"
      println "*                                                          *"
      println "************************************************************"

      def sucursal = 'ANDRADE'

      try{
         exportadorDeVales.exportarSucursal(sucursal)
      }catch (Exception e){
             e.printStackTrace()
     }
     try{
        exportadorDeTraslados.exportarSucursal(sucursal)
     }catch (Exception e){
            e.printStackTrace()
    }

/*
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

*/
    }


}
