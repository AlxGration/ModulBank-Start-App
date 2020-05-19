export const fetchSignUp = (request) =>{
    
    const URL_LOGIN = 'https://localhost:44314/api/registration';

    const params = {
        method: 'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify(request.user)
    };

    return fetch(URL_LOGIN, params)
        .then(response => {
            if (response.ok){
                return response;
            }
            return response.json();
        })
        .catch(error => {
            throw new Error(error.message);
        });
}