import socket
import argparse
import time

from flask import Flask, request, jsonify
from datetime import datetime
import os
from gradientai import Gradient
import asyncio

# Set up environmental variables for Gradient API
token = 'LXkZBp5a1nGCP16xM7QieYKNz2Ns0tOW'
workspace_id = 'b5f958d9-ffd4-41bb-a492-704114e02c8e_workspace'

os.environ['GRADIENT_ACCESS_TOKEN'] = token
os.environ['GRADIENT_WORKSPACE_ID'] = workspace_id

app = Flask(__name__)

# Global variable for the model adapter
new_model_adapter = None

import re
import json

# Default route for the Flask API
@app.route('/')
def index():
    return "Welcome to the Flask API!"

# Process the recipe text
def process_recipe(recipe_text):
    return recipe_text

# Parse the received recipe
def parse_rec(rec_text):
    if len(rec_text.split(":")) > 1:
        return rec_text.split("Dish:")[1]
    return rec_text

# Fetch recipe from the Llama AI model
def fetchRecipeFromLlama(cuisine, budget, dish, allergies="No Allergies"):
    print("Received Request!")
    query = (
        f"[INST]\n"
        f"Write a recipe for a delicious {dish} in the cuisine of {cuisine} within the budget of ${budget} and without any {allergies} following this format:\n"
        # Recipe format details...
    )
    response = new_model_adapter.complete(query=query, max_generated_token_count=500).generated_output
    return response

# Fetch instructions from the Llama AI model
def fetchInstructionsFromLlama(cuisine, dish, ingredients):
    print("Received Ingredients!")
    query = (
        f"[INST]\n"
        f"Write the instructions for a recipe for a delicious {dish} in the cuisine of {cuisine}"
        # Instructions format details...
    )
    response = new_model_adapter.complete(query=query, max_generated_token_count=500).generated_output
    return response

# Fetch groceries from the Llama AI model
def fetchGroceryFromLlama(dish, budget, ingredients):
    print("Received Instructions!")
    query = (
        f"[INST]\n"
        f"Write the groceries for a recipe for a delicious {dish} less than ${budget}"
        # Groceries format details...
    )
    response = new_model_adapter.complete(query=query, max_generated_token_count=500).generated_output
    return response

# Fetch recommendations from the Llama AI model
def fetchRecommendationFromLlama(dishes, cuisine):
    print("Received Dishes!")
    query = (
        f"[INST]\n"
        f"Recommend a dish based on the user's previous preferences: {dishes} in {cuisine}'s cuisine style.\n"
        # Recommendations format details...
    )
    response = new_model_adapter.complete(query=query, max_generated_token_count=32).generated_output
    print(response)
    return response

# Route to get recipe details
@app.route('/getRecipe', methods=['GET'])
def get_res():
    print(request.args.get('dish'))
    global new_model_adapter
    if new_model_adapter is None:
        return jsonify({'error': 'Model adapter not initialized'}), 500
    sel_cuisine = request.args.get('cuisine')
    sel_budget = request.args.get('budget')
    sel_dish = request.args.get('dish')
    sel_allergies = request.args.get('allergies')

    if sel_cuisine is None or sel_budget is None or sel_dish is None:
        return jsonify({'error': 'Missing topic parameter'}), 400

    ingredients = fetchRecipeFromLlama(sel_cuisine, sel_budget, sel_dish, sel_allergies)
    time.sleep(10)  # Simulate processing time
    print("Resuming")
    instructions = fetchInstructionsFromLlama(sel_cuisine, sel_dish, process_recipe(ingredients))

    return jsonify({'ingredients': process_recipe(ingredients), 'instructions': instructions, 'groceries': "groceries"}), 200

# Route to get grocery prices
@app.route('/getPrices', methods=['GET'])
def get_prices():
    global new_model_adapter
    if new_model_adapter is None:
        return jsonify({'error': 'Model adapter not initialized'}), 500
    sel_budget = request.args.get('budget')
    sel_dish = request.args.get('dish')
    sel_ingredients = request.args.get('ingredients')

    if sel_budget is None or sel_dish is None or sel_ingredients is None:
        return jsonify({'error': 'Missing topic parameter'}), 400

    groceries = fetchGroceryFromLlama(sel_dish, sel_budget, sel_ingredients)

    return jsonify({'groceries': groceries}), 200

# Route to get dish recommendations
@app.route('/getRecs', methods=['GET'])
def get_recs():
    global new_model_adapter
    if new_model_adapter is None:
        return jsonify({'error': 'Model adapter not initialized'}), 500
    sel_dishes = request.args.get('dishes')
    sel_cuisine = request.args.get('cuisine')

    if sel_dishes is None or sel_cuisine is None:
        return jsonify({'error': 'Missing topic parameter'}), 400

    recommendation = fetchRecommendationFromLlama(sel_dishes, sel_cuisine)

    return jsonify({'recommendation': parse_rec(recommendation)}), 200

# Prepare the Llama bot with a given name
def prepareLlamaBot(name):
    gradient = Gradient()
    base_model = gradient.get_base_model(base_model_slug="llama2-7b-chat")
    global new_model_adapter
    new_model_adapter = base_model.create_model_adapter(name=name)

# Main function
if __name__ == '__main__':
    default_name = f"Llama_{datetime.now().strftime('%Y%m%d_%H%M%S')}_{socket.gethostname()}"

    parser = argparse.ArgumentParser()
    parser.add_argument('--name', default=default_name, help='Specify a name')
    args = parser.parse_args()

    port_num = 5000
    print(f"Starting Llama bot with name {args.name}...\n This may take a while.")
    prepareLlamaBot(args.name)
    print(f"App running on port {port_num}")
    app.run(port=port_num)
