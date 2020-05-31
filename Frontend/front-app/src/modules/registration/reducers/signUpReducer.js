import * as actions from '../actions/registrationActions';

const initialState = {
    errorMessage:null,
    loading:false,
    isSuccess:false,
};

const signUp = (state = initialState, action) => {
    const responce = action.user;

    console.log('up reducer '+JSON.stringify(action));

    switch(action.type){
        case actions.POST_SIGNUP_REQUEST:
            console.log('POST_SIGNUP_REQUEST');
            return{
                ...state,
                responce,
                loading: true,
            };
        case actions.POST_SIGNUP_SUCCESS:
            console.log('POST_SIGNUP_SUCCESS');
            return{
                ...state,
                loading: false,
                isSuccess: true,
            };
        case actions.POST_SIGNUP_ERROR:
            console.log('POST_SIGNUP_ERROR');
            return{
                ...state,
                loading: false,
                errorMessage: action.data.errorMessage,
            };
        default:
            return state;
    }
};

export default signUp;