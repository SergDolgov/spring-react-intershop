import React from 'react'
import { Tab } from 'semantic-ui-react'
import UserTable from './UserTable'
import ProductTable from './ProductTable'

function AdminTab(props) {
  const { handleInputChange } = props
  const { isBrandsLoading, brands } = props
  const { isUsersLoading, users, userUsernameSearch, handleDeleteUser, handleSearchUser } = props
  const { isProductsLoading, products, productId, productBrand, productTitle, productPoster, productTextSearch, handleAddProduct, handleDeleteProduct, handleSearchProduct } = props

  const panes = [
    {
      menuItem: { key: 'users', icon: 'users', content: 'Users' },
      render: () => (
        <Tab.Pane loading={isUsersLoading}>
          <UserTable
            users={users}
            userUsernameSearch={userUsernameSearch}
            handleInputChange={handleInputChange}
            handleDeleteUser={handleDeleteUser}
            handleSearchUser={handleSearchUser}
          />
        </Tab.Pane>
      )
    },
    {
      menuItem: { key: 'products', icon: 'video camera', content: 'Products' },
      render: () => (
        <Tab.Pane loading={isProductsLoading}>
          <ProductTable
            products={products}
            productId={productId}
            productBrand={productBrand}
            productTitle={productTitle}
            productPoster={productPoster}
            productTextSearch={productTextSearch}
            handleInputChange={handleInputChange}
            handleAddProduct={handleAddProduct}
            handleDeleteProduct={handleDeleteProduct}
            handleSearchProduct={handleSearchProduct}
            brands={brands}

            useProductForm={true}
          />
        </Tab.Pane>
      )
    }
  ]

  return (
    <Tab menu={{ attached: 'top' }} panes={panes} />
  )
}

export default AdminTab