
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<Product>> getProductsByCategory(
			@RequestHeader(value = "API-Key", required = false) String apiKey,
			@PathVariable String category) {
		if (!isValidApiKey(apiKey)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(productService.getProductsByCategory(category));
	}

	@GetMapping("/price")
	public ResponseEntity<List<Product>> getProductsByPriceRange(
			@RequestHeader(value = "API-Key", required = false) String apiKey,
			@RequestParam BigDecimal min,
			@RequestParam BigDecimal max) {
		if (!isValidApiKey(apiKey)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(productService.getProductsByPriceRange(min, max));
	}

	@GetMapping("/{name}")
	public ResponseEntity<Product> getProductByName(
			@RequestHeader(value = "API-Key", required = false) String apiKey,
			@PathVariable String name) {
		if (!isValidApiKey(apiKey)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Optional<Product> product = productService.getProductByName(name);
		return product.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{name}")
	public ResponseEntity<Product> updateProduct(
			@RequestHeader(value = "API-Key", required = false) String apiKey,
			@PathVariable String name,
			@Valid @RequestBody Product product) {
		if (!isValidApiKey(apiKey)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Optional<Product> updatedProduct = productService.updateProduct(name, product);
		return updatedProduct.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<Void> deleteProduct(
			@RequestHeader(value = "API-Key", required = false) String apiKey,
			@PathVariable String name) {
		if (!isValidApiKey(apiKey)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (productService.deleteProduct(name)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	private boolean isValidApiKey(String apiKey) {
		return API_KEY.equals(apiKey);
	}
}
