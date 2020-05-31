import * as actions from '../actions/authenticationActions';

const initialState = {
    errorMessage:null,
    loading:false,
};

const signIn = (state = initialState, action) => {
    const responce = action.user;

    switch(action.type){
        case actions.POST_SIGNIN_REQUEST:
            console.log('POST_SIGNIN_REQUEST');
            return{
                ...state, 
                responce,
                loading:true
            };
        case actions.POST_SIGNIN_SUCCESS:
            console.log('POST_SIGNIN_SUCCESS');
            return{
                ...state, 
                responce,
                loading:false,
            };
        case actions.POST_SIGNIN_ERROR:
            console.log('POST_SIGNIN_ERROR');
            return{
                ...state, 
                responce,
                loading:false,
                errorMessage:action.data.errorMessage,
            };
        default:
            return state;
    }
};

export default signIn;