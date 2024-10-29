
var fileName = 'load'
var interventionId
var showLoading = function (){
    Swal.fire({
        title: 'Veuillez attendre !',
        html: 'Requete en cours',// add html attribute if you want or remove
        allowOutsideClick: false,
        showConfirmButton:false,
        willOpen: () => {
            Swal.showLoading()
        },
    });
}
function rejeter(){
    // // alert(interventionId)
    // interventionId = element.getAttribute('data-id');
    Swal.fire({
        title: "Confirmation",
        text: "Voulez-vous rejeter cette intervention?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: "#4B49AC",
        confirmButtonText: "Oui",
        cancelButtonColor: "#d33",
        cancelButtonText: "Non"
    }).then((result) => {
        if (result.isConfirmed) {
            showLoading()
            // // alert(url)
            rejet()


        }
    });

}
function accepter(element){
    interventionId = element.getAttribute('data-id');
    matricule = element.getAttribute('data-matricule')
    code = element.getAttribute('data-code')

    Swal.fire({
        title: "Confirmation",
        text: "Voulez-vous prendre en charge l'intervention "+interventionId+" de l'etudiant "+ matricule,
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: "#4B49AC",
        confirmButtonText: "Oui",
        cancelButtonColor: "#d33",
        cancelButtonText: "Non"
    }).then((result) => {
        if (result.isConfirmed) {

            showLoading()
            var url = 'http://localhost:9090/api/interventions/prendre-en-charge/'+interventionId+'/'+code
            // // alert(url)

            $.ajax({
                url: url,
                type: 'PUT',
                // data: dataS,
                processData: false, // empêche jQuery de transformer les données en chaîne de requête
                contentType: false,
                success: function(data) {
                    Swal.hideLoading()
                    console.log(data)
                    Swal.fire({
                        title: "Prise en charge!",
                        text: "L'intervention a ete prise",
                        icon: "success"
                    });
                    setTimeout(()=>{
                        location.reload()
                    },1500)
                    // Vous pouvez manipuler les données ici
                },
                error: function(xhr, status, error) {
                    Swal.hideLoading()
                    Swal.fire({
                        title: "Erreur!",
                        text: "L'intervention n'a pas ete prise avec succes",
                        icon: "error"
                    });
                    console.error('Erreur lors de la requête API:', status, error);
                    console.log('Erreur lors de la requête API:', xhr.responseText);
                }
            });


        }
    });
}
function annuler(element){
    interventionId = element.getAttribute('data-id');
    // matricule = element.getAttribute('data-matricule')

    console.log(element.getAttribute('data-matricule'))
    Swal.fire({
        title: "Confirmation",
        text: "Voulez-vous reelement annuler cette intervention?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: "#4B49AC",
        confirmButtonText: "Oui",
        cancelButtonColor: "#d33",
        cancelButtonText: "Non"
    }).then((result) => {
        if (result.isConfirmed) {

            showLoading()
            var url = 'http://localhost:9090/api/interventions/cancel/'+interventionId
            // // alert(url)

            $.ajax({
                url: url,
                type: 'POST',
                // data: dataS,
                processData: false, // empêche jQuery de transformer les données en chaîne de requête
                contentType: false,
                success: function(data) {

                    Swal.hideLoading()
                    console.log(data)
                    Swal.fire({
                        title: "Annulez!",
                        text: "Votre intervention a ete annule avec succes",
                        icon: "success"
                    });
                    setTimeout(()=>{
                        location.reload()
                    },1500)
                    // Vous pouvez manipuler les données ici
                },
                error: function(xhr, status, error) {
                    Swal.hideLoading()
                    Swal.fire({
                        title: "Erreur!",
                        text: "Votre intervention n'a pas pu etre annule",
                        icon: "error"
                    });
                    console.error('Erreur lors de la requête API:', status, error);
                    console.log('Erreur lors de la requête API:', xhr.responseText);
                }
            });


        }
    });
}
function showAlert(element) {
    interventionId = element.getAttribute('data-id');
    const inputField = document.getElementById('float-input');
    const charCount = document.getElementById('charCount');

    console.log(element);
    console.log("ID de l'intervention : " + interventionId);

    // $('#downloads').text('Chargez le fichier');
    $('#description').val('Votre requete est Termine 👍👍👍')
    $('#float-input').val('Cher(e) étudiant(e), \n\n' +
        'Nous sommes heureux de vous informer que le traitement de votre demande d\'intervention a été terminé avec succès.\n\n' +
        'Nous espérons que le problème pour lequel vous avez demandé de l\'aide a été résolu de manière satisfaisante. Si vous avez d\'autres questions ou préoccupations, n\'hésitez pas à nous contacter à tout moment.\n\n' +
        'Merci pour votre collaboration.\n\n'+
        'Cordialement',+
        'Le département'+
        'INSTITUT UNIVERSITAIRE SAINT JEAN');

    inputField.addEventListener('input', function() {
        const count = inputField.value.length;
        charCount.textContent = count;
    });

}
function rejetContenu(element) {
    interventionId = element.getAttribute('data-id');
    const inputField = document.getElementById('float-input1');
    const charCount = document.getElementById('charCount1');

    console.log(element);
    console.log("ID de l'intervention : " + interventionId);

    // $('#downloads').text('Chargez le fichier');
    $('#description1').val('Votre requete est Rejete ❌❌❌')
    $('#float-input1').val('Cher(e) étudiant(e),\n\n' +
        'Nous avons le regret de vous informer que votre intervention a été rejetée.\n\n' +
    'Cordialement',+
        'Le département'+
    'INSTITUT UNIVERSITAIRE SAINT JEAN');



    inputField.addEventListener('input', function() {
        const count = inputField.value.length;
        charCount.textContent = count;
    });

}
var intervention
function repondreIntervention(idIntervention,dataS){
    // alert("envoie")
    console.log(dataS)
    console.log(idIntervention)
    var url = 'http://localhost:9090/api/interventions/Termine/'+idIntervention
    // alert(url)
    showLoading()
    $.ajax({
        url: url,
        type: 'PUT',
        data: dataS,
        processData: false, // empêche jQuery de transformer les données en chaîne de requête
        contentType: false,
        success: function(data) {
            $('#terminerInterventionModal').modal('hide')
            Swal.hideLoading()
            console.log(data)
            Swal.fire({
                title: "Fin d'intervention!",
                text: "L'intervention est termine",
                icon: "success"
            });
            setTimeout(()=>{
                location.reload()
            },1500)

        },
        error: function(xhr, status, error) {
            Swal.fire({
                title: "Fin d'intervention!",
                text: "Erreur lors de lors de la mise en fin de l'inter",
                icon: "error"
            });
            // alert("erreur")
            console.error('Erreur lors de la requête API:', status, error);
            console.log('Erreur lors de la requête API:', xhr.responseText);
        }
    });
}
function rejetIntervention(idIntervention,dataS){
    // alert("envoie")
    console.log(dataS)
    console.log(idIntervention)
    var url = 'http://localhost:9090/api/interventions/rejeter/'+idIntervention
    // alert(url)
    showLoading()
    $.ajax({
        url: url,
        type: 'POST',
        data: dataS,
        processData: false, // empêche jQuery de transformer les données en chaîne de requête
        contentType: false,
        success: function(data) {
            $('#rejetInterventionModal').modal('hide')
            Swal.hideLoading()
            console.log(data)
            Swal.fire({
                title: "Rejet d'intervention!",
                text: "L'intervention est rejete",
                icon: "success"
            });
            setTimeout(()=>{
                location.reload()
            },1500)

        },
        error: function(xhr, status, error) {
            Swal.fire({
                title: "Rejte d'intervention!",
                text: "Erreur lors de lors du rejet de l'intervention",
                icon: "error"
            });
            // alert("erreur")
            console.error('Erreur lors de la requête API:', status, error);
            console.log('Erreur lors de la requête API:', xhr.responseText);
        }
    });
}



var selectedFiles
function loadFileS(event){
    selectedFiles = event.target.files;
    console.log(selectedFiles);

    if (selectedFiles.length > 0) {
        fileName = selectedFiles[0].name + " ' et " + (selectedFiles.length - 1) + " autres fichier(s)'";
        console.log(fileName);

        $('#downloads').text(fileName);
        // Vous pouvez utiliser la variable fileName comme nécessaire ici
    }
}
function envoyerR(){
    // alert("debut")
    var DescriptionIntervention = document.getElementById('float-input').value;
    var description = document.getElementById('description').value;
    console.log(DescriptionIntervention)
    console.log(description)

    var formData = new FormData();

    formData.append('emailContent', DescriptionIntervention);
    formData.append('description', description);

    for (var key in selectedFiles) {
        if (selectedFiles.hasOwnProperty(key)) {
            var file = selectedFiles[key];
            // Faites quelque chose avec chaque fichier
            formData.append('piecesJointes', file);
            console.log(file)
        }
    }
    console.log(interventionId)
    // alert("finRecolte")
    repondreIntervention(interventionId,formData)

}
function rejet(){
    // alert("debut")
    var DescriptionIntervention = document.getElementById('float-input1').value;
    var description = document.getElementById('description1').value;
    console.log(DescriptionIntervention)
    console.log(description)

    var formData = new FormData();

    formData.append('emailContent', DescriptionIntervention);
    formData.append('description', description);

    // for (var key in selectedFiles) {
    //     if (selectedFiles.hasOwnProperty(key)) {
    //         var file = selectedFiles[key];
    //         // Faites quelque chose avec chaque fichier
    //         formData.append('piecesJointes', file);
    //         console.log(file)
    //     }
    // }
    // // alert(interventionId)
    var url = 'http://localhost:9090/api/interventions/rejeter/'+interventionId

    // alert("finRecolte")
    console.log(formData)
    // alert(formData)
    // rejeter(interventionId,formData)
    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        processData: false, // empêche jQuery de transformer les données en chaîne de requête
        contentType: false,
        success: function(data) {
            console.log(data)
            Swal.hideLoading()
            Swal.fire({
                title: "Rejet!",
                text: "Cette intervention a ete rejete ",
                icon: "success"
            });
            setTimeout(()=>{
                location.reload()
            },1500)
            // Vous pouvez manipuler les données ici
        },
        error: function(xhr, status, error) {
            Swal.hideLoading()
            Swal.fire({
                title: "Erreur!",
                text: "Cette intervention n'a pas pu etre rejeter",
                icon: "error"
            });
            console.error('Erreur lors de la requête API:', status, error);
            console.log('Erreur lors de la requête API:', xhr.responseText);
        }
    });

}