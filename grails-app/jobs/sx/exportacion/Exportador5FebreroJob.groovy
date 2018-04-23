package sx.exportacion

class Exportador5FebreroJob {

  def exportadorDeClientes
  def exportadorDeClientesCredito
  def exportadorDeProductos
  def exportadorDeExistencia
  def exportadorDeVales
  def exportadorDeTraslados

    static triggers = {
     cron name:   'exp5Febrero2',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {

            println "************************************************************"
            println "*                                                          *"
            println "*                    Exportando 5 Febrero Dos              *"
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
      }
}
