(function($) {
  'use strict';
  $(function() {
    // Vérifier si le DataTable est déjà initialisé
    if ($.fn.DataTable.isDataTable('#table_id')) {
      $('#table_id').DataTable().clear().destroy();  // Détruire l'instance existante avant de la réinitialiser
    }
    const tables = ['#order-listing', '#table_id'];

    tables.forEach(function(selector) {
      if ($(selector).length) {  // Vérifier si l'élément existe
        $(selector).DataTable({
          "aLengthMenu": [
            [5, 10, 15, -1],
            [5, 10, 15, "All"]
          ],
          "iDisplayLength": 10,
          "language": {
            search: selector === '#table_id' ? 'Rechercher' : 'Search'
          }
        });

        $(selector).each(function() {
          var datatable = $(this);
          var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
          search_input.attr('placeholder', selector === '#table_id' ? 'Rechercher' : 'Search');
          search_input.removeClass('form-control-sm');

          var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
          length_sel.removeClass('form-control-sm');
        });
      }
    });
  });
})(jQuery);

