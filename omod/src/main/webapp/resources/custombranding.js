    function getFileContent() {

       var text = $("#cssFilesList option:selected").attr("title");

       function succes(response) {
              $('#contentBox').text(response);
       }
       ajaxRequest_Get("/openmrs/module/custombranding/CssContent.form?path=" + text, 'text', succes, null);
    }
    function setFileProps() {

        var text = $("#cssFilesList option:selected").attr("title");

        ajaxRequest_Get("/openmrs/module/custombranding/CssContent.form?path=" + text, 'text', null, null);
    }

    function toogleRecursiveSearchingAndList(elementId) {

      function succes(response) {

            document.getElementById(elementId).options.length = 0;

            jQuery.each( $.parseJSON(response), function(i, val) {
                var x = document.getElementById(elementId);
                var option = document.createElement("option");
                option.text = val;
                option.value = val;
                option.title = i;
                x.add(option);
            });
        }

       ajaxRequest_Get("/openmrs/module/custombranding/SearchCssFiles.form", 'text', succes, null);
    }

    function dbRequest(action, optionalFileContent) {

        function func() {
                        location.reload(true);
                      }

        if(action === "replaceCssFile" ){
            if( $("#cssFilesList option:selected").text() !== '' && $("#uploadCssFile").val() !== "" && optionalFileContent !== undefined) {


                var data = {
                    'action': action,
                    'content': optionalFileContent
                }

                ajaxRequest_Post( "/openmrs/module/custombranding/dbRequest.form", 'text', true, data, func, func);

            } else {
                $("#errors").text("You need to choose css file to replace with pointed by you");
            }

        } else if( $("#cssFilesList option:selected").text() !== '') {
                var data = {
                    'action': action,
                    'content':   document.getElementById('contentBox').value
                    }

                 ajaxRequest_Post( "/openmrs/module/custombranding/dbRequest.form", 'text', true, data, func, func);

        }
    }
    function ajaxRequest_Post( _url, _dataType, _async, _data, _succes, _error) {
         $.ajax({
            type: "POST",
            url: _url,
            dataType: _dataType,
            async: _async,
            data: _data,
            success: _succes,
            error: _error
        });
    }

     function ajaxRequest_Get( _url, _dataType, _succes, _error) {
         $.ajax({
             type: "GET",
             url: _url,
             dataType: _dataType,
             async: true,
             success: _succes,
             error: _error
         });
        }

        function readSingleFile(fileElementId) {
              var file = document.getElementById(fileElementId).files[0];
               if (!file) {
                          return;
                        }

              var reader = new FileReader();
              reader.onloadend = function() {
                  content = reader.result;
                  dbRequest('replaceCssFile', content);
              };
              reader.readAsText(file);

        }






