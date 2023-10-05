import React, { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import ProductList from './ProductList'
import { useAuth } from '../context/AuthContext'
import { productApi } from '../misc/ProductApi'
import { handleLogError } from '../misc/Helpers'
import { useCart } from "./useCart";

function UserPage() {
  const Auth = useAuth()
  const user = Auth.getUser()
  const isUser = user.data.rol[0] === 'USER'

  const [products, setProducts] = useState([])
  const [productTextSearch, setProductTextSearch] = useState('')
  const [isProductsLoading, setIsProductsLoading] = useState(false)
  const [productId, setProductId] = useState(0)

  const { addToCart } = useCart(productId)

  const handleClickAddToCart = (event: any) => {
    setProductId(event)
    addToCart(event)
  }

  useEffect(() => {
    handleGetProducts()
  }, [])

  const handleInputChange = (e, { name, value }) => {
    if (name === 'productTextSearch') {
      setProductTextSearch(value)
    }
  }

  const handleGetProducts = async () => {
    setIsProductsLoading(true)
    try {
      const response = await productApi.getProducts(user)
      setProducts(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsProductsLoading(false)
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

  if (!isUser) {
    return <Navigate to='/' />
  }

  return (
    <Container>
      <ProductList
        isProductsLoading={isProductsLoading}
        productTextSearch={productTextSearch}
        products={products}
        handleAddProductToCart={handleClickAddToCart}
        handleInputChange={handleInputChange}
        handleSearchProduct={handleSearchProduct}
      />
    </Container>
  )
}

export default UserPage