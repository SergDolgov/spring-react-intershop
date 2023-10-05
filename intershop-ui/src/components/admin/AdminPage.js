import React, { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'
import AdminTab from './AdminTab'
import { productApi } from '../misc/ProductApi'
import { handleLogError } from '../misc/Helpers'

function AdminPage() {
  const Auth = useAuth()
  const user = Auth.getUser()
  const isAdmin = user.data.rol[0] === 'ADMIN'

  const [users, setUsers] = useState([])
  const [products, setProducts] = useState([])
  const [brands, setBrands] = useState([])
  const [productId, setProductId] = useState('')
  const [productBrand, setProductBrand] = useState('')
  const [productTitle, setProductTitle] = useState('')
  const [productPoster, setProductPoster] = useState('')
  const [productTextSearch, setProductTextSearch] = useState('')
  const [userUsernameSearch, setUserUsernameSearch] = useState('')
  const [isUsersLoading, setIsUsersLoading] = useState(false)
  const [isProductsLoading, setIsProductsLoading] = useState(false)
  const [isBrandsLoading, setIsBrandsLoading] = useState(false)

  useEffect(() => {
    handleGetUsers()
    handleGetBrands()
    handleGetProducts()
  }, [])

  const handleInputChange = (e, { name, value }) => {
    if (name === 'productBrand') {
      setProductBrand(value)
    } else if (name === 'productTitle') {
      setProductTitle(value)
    } else if (name === 'productPoster') {
      setProductPoster(value)
    } else if (name === 'productTextSearch') {
      setProductTextSearch(value)
    } else if (name === 'userUsernameSearch') {
      setUserUsernameSearch(value)
    }
  }

  const handleGetUsers = async () => {
    try {
      setIsUsersLoading(true)
      const response = await productApi.getUsers(user)
      setUsers(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsUsersLoading(false)
    }
  }

  const handleDeleteUser = async (username) => {
    try {
      await productApi.deleteUser(user, username)
      await handleGetUsers()
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleSearchUser = async () => {
    try {
      const response = await productApi.getUsers(user, userUsernameSearch)
      const data = response.data
      const users = Array.isArray(data) ? data : [data]
      setUsers(users)
    } catch (error) {
      handleLogError(error)
      setUsers([])
    }
  }

  const handleGetProducts = async () => {
    try {
      setIsProductsLoading(true)
      const response = await productApi.getProducts(user)
      setProducts(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsProductsLoading(false)
    }
  }

  const handleGetBrands = async () => {
    try {
      setIsBrandsLoading(true)
      const response = await productApi.getBrands(user)
      setBrands(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsBrandsLoading(false)
    }
  }

  const handleDeleteProduct = async (id) => {
    try {
      await productApi.deleteProduct(user, id)
      await handleGetProducts()
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleAddProduct = async () => {
    const trimmedId = productId.trim()
    //const trimmedBrand = productBrand.trim()
    const trimmedBrand = productBrand
    const trimmedTitle = productTitle.trim()
    const trimmedPoster = productPoster.trim()

    if (!(trimmedBrand && trimmedTitle)) {
      return
    }

    const product = {brandId: trimmedBrand, title: trimmedTitle, poster: trimmedPoster }

    try {
      await productApi.addProduct(user, product)
      clearProductForm()
      await handleGetProducts()
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleSearchProduct = async () => {
    try {
      const response = await productApi.getProducts(user, productTextSearch)
      const products = response.data
      setProducts(products)
    } catch (error) {
      handleLogError(error)
      setProducts([])
    }
  }

  const handleBrandChange = (event) => {
    setProductBrand(event.target.value); // Обновляем выбранный бренд при изменении значения в выпадающем списке
  };

  const clearProductForm = () => {
    setProductBrand(0)
    setProductTitle('')
    setProductPoster('')
  }

  if (!isAdmin) {
    return <Navigate to='/' />
  }

  return (
    <Container>
      <AdminTab
        isUsersLoading={isUsersLoading}
        users={users}
        userUsernameSearch={userUsernameSearch}
        handleDeleteUser={handleDeleteUser}
        handleSearchUser={handleSearchUser}

        isProductsLoading={isProductsLoading}
        products={products}
        productId={productId}
        productBrand={productBrand}
        productTitle={productTitle}
        productPoster={productPoster}
        productTextSearch={productTextSearch}
        handleAddProduct={handleAddProduct}
        handleDeleteProduct={handleDeleteProduct}
        handleSearchProduct={handleSearchProduct}

        isBrandsLoading={isBrandsLoading}
        brands={brands}

        handleInputChange={handleInputChange}
      />
    </Container>
  )
}

export default AdminPage