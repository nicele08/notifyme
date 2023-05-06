/**
 * @typedef {import('sequelize').DataTypes} DataTypes
 * @typedef {import('sequelize').Sequelize} Sequelize
 */

/**
 * @param {Sequelize} sequelize
 * @param {DataTypes} DataTypes
 */
module.exports = (sequelize, DataTypes) => {
  const SMSNotification = sequelize.define(
    'SMSNotification',
    {
      id: {
        type: DataTypes.BIGINT,
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
      },
      ownerId: {
        type: DataTypes.BIGINT,
        allowNull: false,
      },
      from: {
        type: DataTypes.STRING,
        unique: true,
      },
      to: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      text: {
        type: DataTypes.TEXT,
      },
      status: {
        type: DataTypes.ENUM('pending', 'sent', 'failed'),
        defaultValue: 'pending',
        comment: 'pending, sent, failed',
      },
    },
    {
      tableName: 'tbl_sms_notifications',
      timestamps: true,
    },
  );

  SMSNotification.associate = models => {
    // associations can be defined here
  };

  return SMSNotification;
};
