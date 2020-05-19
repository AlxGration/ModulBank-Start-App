export const fetchAccounts = (request) =>{

    const URL_GET_ACCOUNTS = 'https://localhost:44314/api/accounts';

    const token = request.token;
    const userId = request.userId;

    const myHeaders = new Headers();
    myHeaders.append('Content-Type', 'application/json');
    myHeaders.append('Authorization', token ? `Bearer ${token}` : "");
    myHeaders.append('ID',  userId ? `${userId}` : "");
    
    const params = {
        method: 'GET',
        headers:myHeaders
    };
    
    return fetch(URL_GET_ACCOUNTS, params)
        .then(response => {return response.json();})
        .catch(error => {
            throw new Error(error.errorMessage);
        });
}