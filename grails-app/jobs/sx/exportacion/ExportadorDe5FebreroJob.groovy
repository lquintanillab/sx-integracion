package sx.exportacion

class ExportadorDe5FebreroJob {

  def exportadorDeClientes
  def exportadorDeClientesCredito
  def exportadorDeProductos
  def exportadorDeExistencia
  def exportadorDeVales
  def exportadorDeTraslados

    static triggers = {
      cron name:   'exp5Febrero',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute()
    {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando 5 Febrero                  *"
      println "*                                                          *"
      println "************************************************************"

      def sucursal = 'CF5FEBRERO'

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

    }
}
