import axios from 'axios';
import keys from '../constants/keys';

export default class LimiterAPI {
  public axios = axios.create({
    baseURL: keys.LIMITER_API,
  });

  // Manage Clients
  public createClient = async (data: any) => {
    const response = await this.axios.post('/api/clients', data);
    return response.data;
  };

  public getClients = async () => {
    const response = await this.axios.get('/api/clients');
    return response.data;
  };

  public getClient = async (apiKey: string) => {
    const response = await this.axios.get(`/api/clients/${apiKey}`);
    return response.data;
  };

  public updateClient = async (id: string, data: any) => {
    const response = await this.axios.put(`/api/clients/${id}`, data);
    return response.data;
  };

  public deleteClient = async (id: string) => {
    const response = await this.axios.delete(`/api/clients/${id}`);
    return response.data;
  };

  // Manage Monthly Limits
  public createMonthlyLimit = async (data: any) => {
    const response = await this.axios.post(
      '/api/monthly-limits',
      data,
    );
    return response.data;
  };

  public getMonthlyLimitsByClientId = async (clientId: string) => {
    const response = await this.axios.get(
      `/api/monthly-limits/${clientId}`,
    );
    return response.data;
  };

  public getMonthlyLimitByClientIdAndMonth = async (
    clientId: string,
    month: string,
  ) => {
    const response = await this.axios.get(
      `/api/monthly-limits/${clientId}/${month}`,
    );
    return response.data;
  };

  public deleteMonthlyLimit = async (id: string) => {
    const response = await this.axios.delete(
      `/api/monthly-limits/${id}`,
    );
    return response.data;
  };

  // Manage Requests
  public createRequest = async (data: any) => {
    const { apiKey, ...rest } = data;
    const response = await this.axios.post(
      `/api/requests?apiKey=${apiKey}`,
      rest,
    );
    return response.data;
  };

  // Manage Request Limits
  public getRequestLimitById = async (id: string) => {
    const response = await this.axios.get(
      `/api/request-limits/${id}`,
    );
    return response.data;
  };

  public createRequestLimit = async (data: any) => {
    const response = await this.axios.post(
      '/api/request-limits',
      data,
    );
    return response.data;
  };

  public updateRequestLimit = async (id: string, data: any) => {
    const response = await this.axios.put(
      `/api/request-limits/${id}`,
      data,
    );
    return response.data;
  };

  public deleteRequestLimit = async (id: string) => {
    const response = await this.axios.delete(
      `/api/request-limits/${id}`,
    );
    return response.data;
  };
}
