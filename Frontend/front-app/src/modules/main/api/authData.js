export const saveAuth = (request) =>{
    
    console.log('save auth '+ JSON.stringify(request.data));

    localStorage.setItem('token', request.data.token);
    localStorage.setItem('userId', request.data.userId);
    localStorage.setItem('expiration', request.data.expiration);
}

//------------------------------------------------------------------------
//
//------------------------------------------------------------------------

export const removeAuth = () =>{
    
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('expiration');

}