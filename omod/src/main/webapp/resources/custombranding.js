    var lastRecursionToogle = false;

    function getFileContent(currentFile) {

       var text = getSelectedItemPath();

       function succes(response) {
              jQuery('#contentBox').text(response);
       }

       ajaxRequest_Get("/openmrs/module/custombranding/CssContent.form?path=" + text + "&recursive=" + lastRecursionToogle, "text", succes, null);

    }
    function setFileProps() {

        var text = getSelectedItemPath();

        ajaxRequest_Get("/openmrs/module/custombranding/CssContent.form?path=" + text + "&" + lastRecursionToogle, 'text', null, null);
    }

    function toogleRecursiveSearchingAndList(elementId) {

      function succes(response) {

            document.getElementById(elementId).options.length = 0;

            jQuery.each( jQuery.parseJSON(response), function(i, val) {
                var x = document.getElementById(elementId);
                var option = document.createElement("option");
                option.text = val;
                option.value = val;
                option.title = i;
                x.add(option);
            });
        }
        lastRecursionToogle = !lastRecursionToogle;
       ajaxRequest_Get("/openmrs/module/custombranding/SearchCssFiles.form?recursive=" + lastRecursionToogle, 'text', succes, null);
    }

    function dbRequest(action, optionalFileContent) {

        function func() {
                        location.reload(true);
                      }
        var path = getSelectedItemPath();
        if(action === "replaceCssFile" ){
            if( path !== '' && jQuery("#uploadCssFile").val() !== "" && optionalFileContent !== undefined) {


                var data = {
                    'action': action,
                    'path': path,
                    'content': optionalFileContent,
                    'recursive': lastRecursionToogle
                }

                ajaxRequest_Post( "/openmrs/module/custombranding/dbRequest.form", 'text', true, data, func, func);

            } else {
                $("#messageBox").text("You need to choose css file to replace with pointed by you");
            }

        } else if( path !== '') {
                var data = {
                    'action': action,
                    'path': path,
                    'content': document.getElementById('contentBox').value,
                    'recursive': lastRecursionToogle
                    }

                 ajaxRequest_Post( "/openmrs/module/custombranding/dbRequest.form", 'text', true, data, func, func);

        }
    }
    function ajaxRequest_Post( _url, _dataType, _async, _data, _succes, _error) {
         jQuery.ajax({
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
        jQuery.ajax({
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
    function getSelectedItemPath() {
        return jQuery('#cssFilesList option:selected').attr("title");
    }



