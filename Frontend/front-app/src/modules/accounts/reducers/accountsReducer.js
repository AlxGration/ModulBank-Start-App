import * as actions from '../actions/accountsActions';

const initialState = {
    accountsList:[],
    errorMessage:null,
    loading:false,
    displayedAccount:{},
};

const accounts = (state = initialState, action) => {
    const responce = action.user;

    switch(action.type){
        case actions.GET_ACCOUNTS_REQUEST:
            console.log('GET_ACCOUNTS_REQUEST ');
            return{
                ...state, responce,
                loading: true,
            };
        case actions.GET_ACCOUNTS_SUCCESS:
            console.log('GET_ACCOUNTS_SUCCESS ');
            return{
                ...state,
                loading: false,
                accountsList: action.data,
                displayedAccount: action.data[0],
            };
        case actions.GET_ACCOUNTS_ERROR:
            console.log('GET_ACCOUNTS_ERROR ');
            return{
                ...state, 
                loading: false,
                errorMessage: action.data.errorMessage,
            };
        case actions.SET_DISPLAYED_ACCOUNT:
            console.log('SET_DISPLAYED_ACCOUNT ');
            return {
                ...state, 
                displayedAccount: state.accountsList.find(i => i.id === action.data.accountId),
            };
        default:
            return state;
    }
};

export default accounts;