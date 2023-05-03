import { Model } from 'sequelize';

export class Custom extends Model {
  /**
   * Helper method for defining associations.
   * This method is not a part of Sequelize lifecycle.
   * The `models/index` file will call this method automatically.
   */
  static associate(models: Record<string, any>) {
    // define association here
  }
}

export type IModel = typeof Custom;
