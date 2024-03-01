import * as types from '../constants/ApplicationsActionTypes';

const initialState = {
  availableApplications: {},
};

export const getAvailableApplicationsArray = (state) => {
  const availableApplicationsById = state.applications?.availableApplications;
  return availableApplicationsById ? Object.values(availableApplicationsById) : [];
};

export const getApplicationInfoById = ({ state, applicationId }) => {
  return state.applications?.availableApplications?.[applicationId];
};

export default function applications(state = initialState, action) {
  const { payload } = action;
  switch (action.type) {
    case types.POPULATE_APPLICATIONS: {
      const availableApplications = payload.applications.reduce((acc, application) => {
        acc[application.id] = {
          id: application.id,
          caption: application.caption,
          iconClassNames: getIconClassNames(application.id),
          requiresLaunchersQRCodeFilter: application.requiresLaunchersQRCodeFilter,
          showFilters: application.showFilters,
          showInMainMenu: application.showInMainMenu,
          applicationParameters: application.applicationParameters,
        };
        return acc;
      }, {});

      return { ...state, availableApplications };
    }
    default:
      return state;
  }
}

// TODO: this shall come from the backend
const getIconClassNames = (applicationId) => {
  switch (applicationId) {
    case 'picking':
      return 'fas fa-box-open';
    case 'distribution':
      return 'fas fa-people-carry';
    case 'mfg':
      return 'fas fa-industry';
    case 'huManager':
      return 'fas fa-boxes';
    case 'workplaceManager':
      return 'fas fa-location';
    case 'scanAnything':
      return 'fas fa-qrcode';
    default:
      return '';
  }
};