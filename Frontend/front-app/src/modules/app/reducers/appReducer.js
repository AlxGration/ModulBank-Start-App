import * as actions from '../actions/appActions';

const getAuthorize = () =>{
    return true;
    try{
        const expiration = localStorage.getItem('expiration');
        const dNow = new Date();

        var datetime_regex = /(\d\d)\.(\d\d)\.(\d\d\d\d)\s(\d\d):(\d\d)/;

        var dExp = datetime_regex.exec(expiration);
        dExp = new Date(dExp[3], dExp[2], dExp[1], dExp[4], dExp[5]);

        if (dExp > dNow) {
            return true;
        }
        return false;
    }catch(error){
        return false;
    }
}

const initialState = {
    authorized: getAuthorize(),
};

const app = (state = initialState, action) => {
    const responce = action.data;

    switch(action.type){
        case actions.LOGIN_SAVE_REQUEST:
            console.log('LOGIN_SAVE_REQUEST');
            return{
                ...state, responce,
            };
        case actions.LOGIN_SAVE_SUCCESS:
            console.log('LOGIN_SAVE_SUCCESS');
            return{
                ...state, responce,
                authorized:true
            };
        case actions.LOGOUT_REQUEST:
            console.log('LOGOUT_REQUEST');
            return{
                ...state,
            };
        case actions.LOGOUT_SUCCESS:
            console.log('LOGOUT_SUCCESS');
            return{
                ...state,
                authorized:false,
            };
        default:
            return state;
    }
};

export default app;