package org.lijun.kotlin_demos.coroutines.eg.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import org.lijun.kotlin_demos.R
import org.lijun.kotlin_demos.coroutines.eg.viewmodel.NormalUserViewModel
import org.lijun.kotlin_demos.databinding.ActivityUserInformationBinding

class UserInformationActivity : AppCompatActivity() {
    private val normalUserViewModel: NormalUserViewModel by viewModels()
    private lateinit var binding: ActivityUserInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        binding = DataBindingUtil.setContentView<ActivityUserInformationBinding>(
            this,
            R.layout.activity_user_information
        )
        binding.userInfoViewModel = normalUserViewModel
        binding.lifecycleOwner = this
        binding.button.setOnClickListener {
            Toast.makeText(this, "点击了", Toast.LENGTH_LONG).show();
            normalUserViewModel.getNormalUser(1L)
        }
    }
}